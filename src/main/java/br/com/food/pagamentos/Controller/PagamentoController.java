package br.com.food.pagamentos.Controller;

import br.com.food.pagamentos.Service.PagamentoService;

import br.com.food.pagamentos.dto.ItemPedidoDTO;
import br.com.food.pagamentos.dto.PagamentoDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    @Autowired
    private RestTemplate restTemplate;


    @GetMapping
    public Page<PagamentoDto> listar(@PageableDefault(size = 10) Pageable paginacao) {
        return pagamentoService.obterTodos(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDto> detalhar(@PathVariable @NotNull Long id) {
        PagamentoDto dto = pagamentoService.obterPorId(id);
        ResponseEntity<List<ItemPedidoDTO>> response = restTemplate.exchange(
                "http://localhost:8082/pedidos-ms/pedidos/" + dto.getPedidoId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ItemPedidoDTO>>() {
                }
        );

        dto.setItens(response.getBody());

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PagamentoDto> cadastrar(@RequestBody @Valid PagamentoDto dto, UriComponentsBuilder uriBuilder) {
        PagamentoDto pagamento = pagamentoService.criarPagamento(dto);
        URI endereco = uriBuilder.path("/pagamentos/{id}").buildAndExpand(pagamento.getId()).toUri();
        return ResponseEntity.created(endereco).body(pagamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDto> atualizar(@PathVariable @NotNull Long id, @RequestBody @Valid PagamentoDto dto) {
        PagamentoDto atualizado = pagamentoService.atualizarPagamento(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PagamentoDto> remover(@PathVariable @NotNull Long id) {
        pagamentoService.excluirPagamento(id);
        return ResponseEntity.noContent().build();
    }

    @CircuitBreaker(name = "atualizaPedido", fallbackMethod = "PAGAMENTOAUTORIZADOINTEGRACAOPENDENTE")
    @PatchMapping("/{id}/confirmar")
    public void confirmarPagamento(@PathVariable @NotNull Long id){
        pagamentoService.confirmarPagamento(id);
    }

    public void PAGAMENTOAUTORIZADOINTEGRACAOPENDENTE(Long id, Exception e){
        pagamentoService.alteraStatus(id);
    }
}
