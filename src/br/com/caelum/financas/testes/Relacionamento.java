package br.com.caelum.financas.testes;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.TipoMovimentacao;
import br.com.caelum.financas.util.JPAUtil;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.EntityManager;

public class Relacionamento {

  public static void main(String[] args) {

    Conta conta = new Conta();
    conta.setAgencia("0102");
    conta.setBanco("Itau");
    conta.setNumero("1234");

    Movimentacao movimentacao = new Movimentacao();
    movimentacao.setData(Calendar.getInstance());
    movimentacao.setDescricao("Churrascaria");
    movimentacao.setTipoMovimentacao(TipoMovimentacao.SAIDA);
    movimentacao.setValor(new BigDecimal("200.0"));

    movimentacao.setConta(conta);

    EntityManager em = new JPAUtil().getEntityManager();
    em.getTransaction().begin();

    em.persist(conta);
    em.persist(movimentacao);

    em.getTransaction().commit();
    em.close();

  }
}
