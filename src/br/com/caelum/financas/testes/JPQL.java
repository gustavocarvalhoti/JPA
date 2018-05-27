package br.com.caelum.financas.testes;

import br.com.caelum.financas.modelo.Categoria;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.TipoMovimentacao;
import br.com.caelum.financas.util.JPAUtil;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class JPQL {

    public static void main(String[] args) {
        EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();

        //ex01(em);
        //ex02(em);
        //ex03(em);
        //ex04(em);
        //ex05(em);
        //ex06(em);

        em.getTransaction().commit();
        em.close();
    }

    private static void ex01(EntityManager em) {
        String jpql = "select m from Movimentacao m where m.id = 1";
        Query query = em.createQuery(jpql);
        ((List<Movimentacao>) query.getResultList()).stream().forEach(r -> System.out.println("Resultado -> " + r.getDescricao()));
    }

    private static void ex02(EntityManager em) {
        Conta conta = new Conta();
        conta.setId(11);

        String jpql = "select m from Movimentacao m where m.conta = :pConta and m.tipoMovimentacao = :pTipo order by m.valor desc";
        Query query = em.createQuery(jpql);
        query.setParameter("pConta", conta);
        query.setParameter("pTipo", TipoMovimentacao.ENTRADA);

        ((List<Movimentacao>) query.getResultList()).stream().forEach(r -> System.out.println("Resultado -> " + r.getValor()));
    }

    // Select em um campo @ManyToMany
    private static void ex03(EntityManager em) {
        Categoria categoria = new Categoria();
        categoria.setId(1);

        String jpql = "select m from Movimentacao m join m.categoria c where c = :pCategoria order by m.valor desc";
        Query query = em.createQuery(jpql);
        query.setParameter("pCategoria", categoria);

        ((List<Movimentacao>) query.getResultList()).stream().forEach(r -> System.out.println("Resultado -> " + r.getValor()));
    }

    private static void ex04(EntityManager em) {
        // Managed
        Movimentacao movimentacao = em.find(Movimentacao.class, 2);
        Conta conta = movimentacao.getConta();
        conta.getMovimentacoes().stream().forEach(m -> System.out.println(m.getValor()));
    }

    private static void ex05(EntityManager em) {
        String jpql = "select c from Conta c";
        Query query = em.createQuery(jpql);
        printContas(query.getResultList());
    }

    private static void ex06(EntityManager em) {
        // Transforma em Eager
        // Lista todos os bancos, mesmo que ele não tenha movimentações
        // Left join mostra o da esquerda mesmo que o da direita não exista
        String jpql = "select distinct  c from Conta c left join fetch c.movimentacoes";
        Query query = em.createQuery(jpql);
        printContas(query.getResultList());

    }

    private static void printContas(List<Conta> contas) {
        contas.forEach(c -> {
            System.out.println("****************************");
            System.out.println("Banco: " + c.getBanco());
            System.out.println("Movimentações");
            c.getMovimentacoes().stream().forEach(m -> System.out.println(m.getValor()));
        });
    }
}