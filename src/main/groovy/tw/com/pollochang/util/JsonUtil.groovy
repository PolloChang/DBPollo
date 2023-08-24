package tw.com.pollochang.util


import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper

class JsonUtil {
    /**
     * 物件轉JSON
     * @param obj Bean 物件
     * @return JSON
     */
    static String objectToJSON(Object obj){
        ObjectMapper mapper = new ObjectMapper()
        String jsonInString = ""

        try {
            jsonInString = mapper.writeValueAsString(obj)
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e)
        }

        return jsonInString
    }

    /**
     * JSON轉物件
     * @param json json 字串
     * @param className 對應的Bean
     * @return 物件
     * @throws JsonProcessingException 轉換錯誤
     */
    static Object jsonToObject(String json,Class<?> className) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper()
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        if(json != null){
            return mapper.readValue(json, className)
        }
        return null
    }
}
