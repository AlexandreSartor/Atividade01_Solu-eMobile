// Classe Produto
data class Produto(
    val id: Int,
    val nome: String,
    val preco: Double,
    var estoque: Int
) {
    fun exibirDetalhes() {
        println("ID: $id | $nome | R$ $preco | Estoque: $estoque")
    }
}

// Classe Cliente
class Cliente(
    val id: Int,
    val nome: String,
    var saldo: Double
) {
    fun adicionarSaldo(valor: Double) {
        saldo += valor
        println("Saldo de $nome atualizado para R$ $saldo")
    }
}

// Classe Carrinho
class CarrinhoDeCompras {
    private val itens = mutableListOf<Pair<Produto, Int>>()

    fun adicionarProduto(produto: Produto, quantidade: Int) {
        if (quantidade <= produto.estoque) {
            itens.add(Pair(produto, quantidade))
            println("${quantidade}x ${produto.nome} adicionado ao carrinho")
        } else {
            println("Estoque insuficiente de ${produto.nome}")
        }
    }

    fun removerProduto(produto: Produto) {
        val item = itens.find { it.first == produto }
        if (item != null) {
            itens.remove(item)
            println("${produto.nome} removido do carrinho")
        }
    }

    fun calcularTotal(): Double {
        return itens.sumOf { it.first.preco * it.second }
    }

    fun exibirCarrinho() {
        if (itens.isEmpty()) {
            println("Carrinho vazio")
        } else {
            println("Itens no carrinho:")
            for ((produto, qtd) in itens) {
                println("${produto.nome} - $qtd x R$ ${produto.preco} = R$ ${produto.preco * qtd}")
            }
            println("Total: R$ ${calcularTotal()}")
        }
    }

    fun getItens() = itens
}

// Classe Loja
class Loja(private val produtos: List<Produto>) {

    fun listarProdutos() {
        println("Produtos disponíveis na loja:")
        for (produto in produtos) {
            produto.exibirDetalhes()
        }
    }

    fun finalizarCompra(cliente: Cliente, carrinho: CarrinhoDeCompras) {
        val total = carrinho.calcularTotal()

        if (total > cliente.saldo) {
            println("Saldo insuficiente! Compra não realizada.")
            return
        }

        for ((produto, qtd) in carrinho.getItens()) {
            if (qtd > produto.estoque) {
                println("Produto ${produto.nome} sem estoque suficiente!")
                return
            }
        }

        // Atualiza saldo e estoque
        cliente.saldo -= total
        for ((produto, qtd) in carrinho.getItens()) {
            produto.estoque -= qtd
        }

        println("Compra realizada com sucesso! Saldo restante de ${cliente.nome}: R$ ${cliente.saldo}")
    }
}

