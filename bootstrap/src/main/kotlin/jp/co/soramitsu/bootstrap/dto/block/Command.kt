package jp.co.soramitsu.bootstrap.dto.block

import java.util.HashMap
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("addPeer", "createRole", "createDomain", "createAsset", "createAccount")
class Command {

    @JsonProperty("addPeer")
    @get:JsonProperty("addPeer")
    @set:JsonProperty("addPeer")
    var addPeer: jp.co.soramitsu.bootstrap.dto.block.AddPeer? = null
    @JsonProperty("createRole")
    @get:JsonProperty("createRole")
    @set:JsonProperty("createRole")
    var createRole: jp.co.soramitsu.bootstrap.dto.block.CreateRole? = null
    @JsonProperty("createDomain")
    @get:JsonProperty("createDomain")
    @set:JsonProperty("createDomain")
    var createDomain: jp.co.soramitsu.bootstrap.dto.block.CreateDomain? = null
    @JsonProperty("createAsset")
    @get:JsonProperty("createAsset")
    @set:JsonProperty("createAsset")
    var createAsset: jp.co.soramitsu.bootstrap.dto.block.CreateAsset? = null
    @JsonProperty("createAccount")
    @get:JsonProperty("createAccount")
    @set:JsonProperty("createAccount")
    var createAccount: jp.co.soramitsu.bootstrap.dto.block.CreateAccount? = null
    @JsonIgnore
    private val additionalProperties = HashMap<String, Any>()

    @JsonAnyGetter
    fun getAdditionalProperties(): Map<String, Any> {
        return this.additionalProperties
    }

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        this.additionalProperties[name] = value
    }

}
