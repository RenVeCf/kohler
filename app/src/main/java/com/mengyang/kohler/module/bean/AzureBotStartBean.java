package com.mengyang.kohler.module.bean;

/**
 * Created by MengYang on 2018/5/22.
 */
public class AzureBotStartBean {

    /**
     * conversationId : BBXporxIjPNKEHSwCK6BZJ
     * token : yTlSJIGr5Ak.dAA.QgBCAFgAcABvAHIAeABJAGoAUABOAEsARQBIAFMAdwBDAEsANgBCAFoASgA.SSogNL7x0wE.4qeh_4N1eKE.dEDgB2CYVUu1cwqyvvIYzVonNyglrD90Gq8lM7lLQCw
     * expires_in : 1800
     * streamUrl : wss://directline.botframework.com/v3/directline/conversations/BBXporxIjPNKEHSwCK6BZJ/stream?watermark=-&t=yTlSJIGr5Ak.dAA.QgBCAFgAcABvAHIAeABJAGoAUABOAEsARQBIAFMAdwBDAEsANgBCAFoASgA.STwBJ7rx0wE.7xYBa2q4JX4.u4fCHhW-nwTt__0o9Arqpr5v4u52ZAWn4E0bs2qhd2s
     * referenceGrammarId : e0ae3232-aaac-7150-a9fc-cc78fcee54dc
     */

    private String conversationId;
    private String token;
    private int expires_in;
    private String streamUrl;
    private String referenceGrammarId;

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getReferenceGrammarId() {
        return referenceGrammarId;
    }

    public void setReferenceGrammarId(String referenceGrammarId) {
        this.referenceGrammarId = referenceGrammarId;
    }

    @Override
    public String toString() {
        return "BotBean{" +
                "conversationId='" + conversationId + '\'' +
                ", token='" + token + '\'' +
                ", expires_in=" + expires_in +
                ", streamUrl='" + streamUrl + '\'' +
                ", referenceGrammarId='" + referenceGrammarId + '\'' +
                '}';
    }
}
