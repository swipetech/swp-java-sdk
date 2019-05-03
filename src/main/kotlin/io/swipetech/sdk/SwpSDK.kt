package io.swipetech.sdk

import ApiKeyHeader
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.swipetech.commons.actions.sign
import io.swipetech.commons.dtos.*
import org.json.JSONObject
import SwpTimestamp
import SwpSignature
import java.time.Instant

data class SuccessResponse<T>(
    val data: T? = null,
    val pagination: PaginationResponse? = null,
    val error: ErrorDTO? = null
) {
    companion object {
        fun <T> fromDataResp(res: ResponseDTO<T>): SuccessResponse<T> {
            return SuccessResponse(res.data)
        }

        fun <T> fromDataListRes(res: ResponseListDTO<T>): SuccessResponse<List<DataDTOReceipt<T>>> {
            return SuccessResponse(res.data, res.pagination)
        }
    }
}

data class Swipe(
    private val apiKey: String,
    private val secret: String,
    private val lang: String = AcceptLanguage.EN_US,
    private val sandbox: Boolean = false,
    private val debug: Boolean = false,
    private val host: String? = null
) {

    @Throws(ErrorDTO::class)
    fun checkSignature(signature: String, info: SignatureInfo): Boolean =
         signature == sign(
            secret = this.secret,
            method = info.method,
            timestamp = info.timestamp,
            body = info.bodyString,
            path = info.path
        )

    @Throws(ErrorDTO::class)
    fun getOrganization(): SuccessResponse<DataDTOReceipt<OrgDTO>> {
        return SuccessResponse.fromDataResp(
            request(method = Methods.GET, path = "organizations")
        )
    }

    @Throws(ErrorDTO::class)
    fun resetOrganization() {
        return request(method = Methods.POST, path = "organizations/reset")
    }

    @Throws(ErrorDTO::class)
    fun getToken(): SuccessResponse<DataDTO<ResponseToken>> {
        return SuccessResponse.fromDataResp(
            request(
                method = Methods.GET,
                path = "organizations/revoke"
            )
        )
    }

    @Throws(ErrorDTO::class)
    fun revokeCredentials(token: String): SuccessResponse<Any> {
        return request(method = Methods.POST, path = "organizations/revoke/$token")
    }

    @Throws(ErrorDTO::class)
    fun createAccount(acc: NewAccDTO? = null): SuccessResponse<DataDTOReceipt<AccountDTO>>  {
        val json = acc?.let {
            val mapper = jacksonObjectMapper()
            mapper.writeValueAsString(it)
        } ?: "{}"
        return SuccessResponse.fromDataResp(
            request(method = Methods.POST, path = "accounts", json = JSONObject(json))
        )
    }

    @Throws(ErrorDTO::class)
    fun destroyAccount(accountID: String): SuccessResponse<DataDTOReceipt<AccountDTO>> {
        return request(method = Methods.DELETE, path = "accounts/$accountID")
    }

    @Throws(ErrorDTO::class)
    fun getAllAccounts(
        pagination: PaginationParams? = null,
        filter: FilterDTO? = null
    ): SuccessResponse<List<DataDTOReceipt<AccountDTO>>> {
        return SuccessResponse.fromDataListRes(
            request(
                method = Methods.GET,
                path = "accounts",
                pagination = pagination,
                filter = filter
            )
        )
    }

    @Throws(ErrorDTO::class)
    fun getAllAssets(
        pagination: PaginationParams? = null,
        filter: FilterDTO? = null
    ): SuccessResponse<List<DataDTOReceipt<AssetDTO>>> {
        return SuccessResponse.fromDataListRes(
            request(
                method = Methods.GET,
                path = "assets",
                pagination = pagination,
                filter = filter
            )
        )
    }

    @Throws(ErrorDTO::class)
    fun getAllTransfers(
        accountID: String,
        pagination: PaginationParams? = null
    ): SuccessResponse<List<DataDTOReceipt<TransferDTO>>> {
        return SuccessResponse.fromDataListRes(
            request(
                method = Methods.GET,
                path = "accounts/$accountID/transfers",
                pagination = pagination
            )
        )
    }

    @Throws(ErrorDTO::class)
    fun getAccount(id: String): SuccessResponse<DataDTOReceipt<AccountDTO>> {
        return request(
            method = Methods.GET,
            path = "accounts/$id"
        )
    }

    @Throws(ErrorDTO::class)
    fun getAccountByAlias(alias: String): SuccessResponse<DataDTOReceipt<AccountDTO>> {
        return request(
            method = Methods.GET,
            path = "accounts/alias/$alias"
        )
    }

    @Throws(ErrorDTO::class)
    fun issueAsset(asset: NewAssetDTO): SuccessResponse<DataDTOReceipt<AssetDTO>> {
        return request(
            method = Methods.POST,
            path = "assets",
            json = JSONObject(asset)
        )
    }

    @Throws(ErrorDTO::class)
    fun makeTrailTransfer(trailTransfer: NewTrailTransferDTO): SuccessResponse<DataDTOReceipt<TrailTransferDTO>> {
        val mapper = jacksonObjectMapper()
        val json = mapper.writeValueAsString(trailTransfer)
        return request(method = Methods.POST, path = "trail-transfers", json = JSONObject(json))
    }

    @Throws(ErrorDTO::class)
    fun makeTransfers(transfer: NewTransferBatchDTO): SuccessResponse<DataDTOReceipt<TransferBatchDTO>> {
        val mapper = jacksonObjectMapper()
        val json = mapper.writeValueAsString(transfer)
        return request(method = Methods.POST, path = "transfers", json = JSONObject(json))
    }

    @Throws(ErrorDTO::class)
    fun getTransfer(id: String): SuccessResponse<DataDTOReceipt<TransferBatchDTO>> {
        return request(method = Methods.GET, path = "transfers/$id")
    }

    fun getTrailTransferById(id: String): SuccessResponse<DataDTOReceipt<TrailTransferDTO>> {
        return request(method = Methods.GET, path = "trail-transfers/$id")
    }

    @Throws(ErrorDTO::class)
    fun updateTags(id: String, tags: List<String>): SuccessResponse<DataDTOReceipt<TagsDTO>> {
        val json = JSONObject(NewTagsDTO(tags = tags))
        return request(method = Methods.PUT, path = "tags/$id", json = json)
    }

    @Throws(ErrorDTO::class)
    fun makeActionBatch(batch: NewActionBatchDTO): SuccessResponse<DataDTOReceipt<ActionBatchDTO>> {
        val mapper = jacksonObjectMapper()
        val json = mapper.writeValueAsString(batch)

        return request(method = Methods.POST, path = "actions", json = JSONObject(json))
    }

    @Throws(ErrorDTO::class)
    fun getActionBatch(id: String): SuccessResponse<DataDTOReceipt<ActionBatchDTO>> {
        return request(method = Methods.GET, path = "actions/$id")
    }

    @Throws(ErrorDTO::class)
    fun createWebhook(hook: NewWebhookDTO): SuccessResponse<DataDTO<WebhookDTO>> {
        val json = hook.let {
            val mapper = jacksonObjectMapper()
            mapper.writeValueAsString(it)
        }
        return SuccessResponse.fromDataResp(
            request(method = Methods.POST, path = "webhooks", json = JSONObject(json))
        )
    }

    @Throws(ErrorDTO::class)
    fun deleteWebHook(id: String): SuccessResponse<Any> {
        return request(method = Methods.DELETE, path = "webhooks/$id")
    }

    @Throws(ErrorDTO::class)
    fun getWebhook(id: String): SuccessResponse<DataDTO<WebhookDTO>> {
        return request(method = Methods.GET, path = "webhooks/$id")
    }

    @Throws(ErrorDTO::class)
    fun getUserPSPInfo(instantID: String): SuccessResponse<UserPSPInfo> {
        return request(method = Methods.GET, path = "psp/instant-id/$instantID")
    }

    private inline fun <reified T : Any> request(
        method: Method,
        path: String,
        pagination: PaginationParams? = null,
        json: JSONObject? = null,
        filter: FilterDTO? = null
    ): T {

        val body = json ?: ""
        val timestamp = Instant.now().epochSecond.toString()
        val signature = sign(
            secret = secret,
            method = method,
            path = "/$path",
            body = body.toString(),
            timestamp = timestamp
        )

        val params = mutableMapOf<String, String>()

        pagination?.let { p ->
            params["limit"] = p.limit
            p.startingAfter?.let { params["starting_after"] = it }
        }

        filter?.let { f ->
            f.tag?.let { params["tag"] = it }
        }

        val headers = mapOf(
            ApiKeyHeader to apiKey,
            SwpTimestamp to timestamp,
            SwpSignature to signature,
            "accept-language" to lang
        )

        if (debug) {
            println("Performing request:")
            println("--path:$path")
            println("--params:$params")
            println("--headers:$headers")
            println("--body:$json")
        }

        val host = host ?: when (sandbox) {
            true -> Host.SANDBOX
            false -> Host.PRODUCTION
        }

        val resp = khttp.request(
            method = method,
            url = "$host/$path",
            params = params,
            headers = headers,
            json = json,
            timeout = 120.0
        )

        checkError(resp)

        return getResp(resp, debug)
    }
}
