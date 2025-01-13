package br.com.food.pagamentos.Service;


import br.com.food.pagamentos.Model.Pagamento;
import br.com.food.pagamentos.Model.Status;
import br.com.food.pagamentos.Repository.PagamentoRepository;


import br.com.food.pagamentos.dto.PagamentoDto;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class PagamentoService {
    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    private ModelMapper modelMapper;


    public Page<PagamentoDto> obterTodos(Pageable paginacao) {
        return pagamentoRepository.findAll(paginacao).map(p -> modelMapper.map(p,PagamentoDto.class));
    }


    public PagamentoDto obterPorId(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public PagamentoDto criarPagamento(PagamentoDto dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setStatus(Status.CRIADO);
        pagamentoRepository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public PagamentoDto atualizarPagamento(Long id, PagamentoDto dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setId(id);
        pagamento = pagamentoRepository.save(pagamento);
        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public void excluirPagamento(Long id) {
        pagamentoRepository.deleteById(id);
    }
}
