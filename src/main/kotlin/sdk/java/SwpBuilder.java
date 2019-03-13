package sdk.java;


import sdk.AcceptLanguage;

public class SwpBuilder {

    private String apiKey;
    private String secret;
    private String lang = AcceptLanguage.EN_US;
    private boolean isSandbox;
    private boolean isDebug;
    private String host;

    public SwpBuilder setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public SwpBuilder setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    public SwpBuilder setLang(String lang) {
        this.lang = lang;
        return this;
    }

    public SwpBuilder setEnvAsSandbox() {
        this.isSandbox = true;
        return this;
    }

    public SwpBuilder setHost(String host) {
        this.host = host;
        return this;
    }

    public SwpBuilder enableDebug() {
        this.isDebug = true;
        return this;
    }

    public sdk.Swipe build() {
        return new sdk.Swipe(
                apiKey,
                secret,
                lang,
                isSandbox,
                isDebug,
                host);
    }
}