import io.swipetech.commons.actions.sign
import io.swipetech.commons.dtos.SignatureInfo
import io.swipetech.sdk.SwpBuilder
import org.junit.Assert
import org.junit.Test
import java.time.Instant

class Commons {

    @Test
    fun checkSignature() {

        val secret = "a0ff9e92148df21866bda2df9465e72654f02b0135c7242e782358adaadd55e1"

        val swp = SwpBuilder()
            .setApiKey("")
            .setSecret(secret)
            .setHost("")
            .build()

        val method = "GET"
        val path = "/"
        val timestamp = Instant.now().epochSecond.toString()
        val body = ""

        val signInfo = SignatureInfo(
            method = method,
            path = path,
            timestamp = timestamp,
            bodyString = body
        )

        val signature = sign(
            secret = secret,
            timestamp = timestamp,
            path = path,
            body = body,
            method = method
        )

        Assert.assertTrue(swp.checkSignature(signature = signature, info = signInfo))
    }
}
