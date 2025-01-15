package br.com.food.pagamentos.http;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("pedidos-ms")
public interface PedidoClient {
    @RequestMapping(method = RequestMethod.PUT,value = "/pedidos/{id}/pago")
    void atualizarPagamento(@PathVariable Long id);


}
