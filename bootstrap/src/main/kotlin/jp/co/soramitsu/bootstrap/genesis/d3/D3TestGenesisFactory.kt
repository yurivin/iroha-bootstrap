package jp.co.soramitsu.bootstrap.genesis.d3

import com.google.protobuf.util.JsonFormat
import jp.co.soramitsu.iroha.java.Transaction
import jp.co.soramitsu.iroha.java.TransactionBuilder
import jp.co.soramitsu.iroha.testcontainers.detail.GenesisBlockBuilder
import jp.co.soramitsu.bootstrap.genesis.*
import java.util.*

class D3TestGenesisFactory : jp.co.soramitsu.bootstrap.genesis.GenesisInterface {
    override fun getAccountsNeeded(): List<jp.co.soramitsu.bootstrap.dto.AccountPrototype> = D3TestContext.d3neededAccounts

    override fun getProject(): String {
        return "D3"
    }

    override fun getEnvironment(): String {
        return "test"
    }

    override fun createGenesisBlock(
        accounts: List<jp.co.soramitsu.bootstrap.dto.IrohaAccountDto>,
        peers: List<jp.co.soramitsu.bootstrap.dto.Peer>,
        blockVersion: String
    ): String {
        val transactionBuilder = Transaction.builder(null)

        createPeers(peers, transactionBuilder)
        createRoles(transactionBuilder)
        createDomains(transactionBuilder)
        createAssets(transactionBuilder)
        createAccounts(transactionBuilder, accounts)

        val blockBuilder = GenesisBlockBuilder().addTransaction(transactionBuilder.build().build())
        val block = blockBuilder.build()
        val payload = JsonFormat.printer().omittingInsignificantWhitespace().print(block)
        return "{\"blockV1\": $payload}"
    }

    private fun createAccounts(
        transactionBuilder: TransactionBuilder,
        accountsList: List<jp.co.soramitsu.bootstrap.dto.IrohaAccountDto>
    ) {
        val accountsMap: HashMap<String, jp.co.soramitsu.bootstrap.dto.IrohaAccountDto> = HashMap()
        accountsList.forEach { accountsMap.putIfAbsent("${it.title}@${it.domainId}", it) }

        val accountErrors = checkNeendedAccountsGiven(accountsMap)
        if (accountErrors.isNotEmpty()) {
            throw jp.co.soramitsu.bootstrap.error.AccountException(accountErrors.toString())
        }
        D3TestContext.d3neededAccounts.forEach {
            val accountDto = accountsMap[it.id]
            if(accountDto  != null) {
                if(!accountDto.creds.isEmpty())  {
                    transactionBuilder.createAccount(it.title, it.domainId, getIrohaPublicKeyFromHexString(accountDto.creds.get(0).public))
                } else {
                    throw jp.co.soramitsu.bootstrap.error.AccountException("Needed account keys are not received: ${it.id}")
                }

            } else {
                throw jp.co.soramitsu.bootstrap.error.AccountException("Needed account keys are not received: ${it.id}")
            }
        }
    }

    private fun checkNeendedAccountsGiven(accountsMap: HashMap<String, jp.co.soramitsu.bootstrap.dto.IrohaAccountDto>): List<String> {
        val loosed = ArrayList<String>()
        D3TestContext.d3neededAccounts.forEach {
            if (!accountsMap.containsKey(it.id)) {
                loosed.add("Needed account keys are not received: ${it.id}")
            }
        }
        return loosed
    }


    private fun createAssets(builder: TransactionBuilder) {
        createAsset(builder, "xor", "sora", 0)
        createAsset(builder, "ether", "ethereum", 18)
        createAsset(builder, "btc", "bitcoin", 8)
    }

    private fun createDomains(builder: TransactionBuilder) {
        createDomain(builder, "notary", "none")
        createDomain(builder, "d3", "client")
        createDomain(builder, "btcSession", "none")
        createDomain(builder, "ethereum", "none")
        createDomain(builder, "sora", "sora_client")
        createDomain(builder, "bitcoin", "client")
        createDomain(builder, "btcSignCollect", "none")
    }


    private fun createRoles(builder: TransactionBuilder) {
        D3TestContext.createNotaryRole(builder)
        D3TestContext.createRelayDeployerRole(builder)
        D3TestContext.createEthTokenListStorageRole(builder)
        D3TestContext.createRegistrationServiceRole(builder)
        D3TestContext.createClientRole(builder)
        D3TestContext.createWithdrawalRole(builder)
        D3TestContext.createSignatureCollectorRole(builder)
        D3TestContext.createVacuumerRole(builder)
        D3TestContext.createNoneRole(builder)
        D3TestContext.createTesterRole(builder)
        D3TestContext.createWhiteListSetterRole(builder)
        D3TestContext.createRollBackRole(builder)
        D3TestContext.createNotaryListHolderRole(builder)
        D3TestContext.createSoraClientRole(builder)
        D3TestContext.createBtcFeeRateSetterRole(builder)
    }

}


