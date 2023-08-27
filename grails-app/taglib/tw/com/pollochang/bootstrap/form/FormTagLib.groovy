package tw.com.pollochang.bootstrap.form

import groovy.transform.CompileStatic
import org.grails.buffer.GrailsPrintWriter
import org.grails.encoder.CodecLookup
import org.grails.encoder.Encoder
import org.springframework.web.servlet.support.RequestDataValueProcessor

import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat


/**
 * 系統表單tag
 * doc: https://getbootstrap.com/docs/5.0/forms/overview/
 */
class FormTagLib {

    CodecLookup codecLookup

    RequestDataValueProcessor requestDataValueProcessor

    private List<String> booleanAttributes = ['disabled', 'checked', 'readonly','required']

    static defaultEncodeAs = [taglib:'none']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
    static namespace = "dict"

    /**
     * 表單常用 table
     */
    Closure fromTable = {attrs,body ->
        String name  = attrs.remove("name")?:""
        String id  = attrs.remove("name")?:name
        String classes  = attrs.remove("class")?:""
        out << """
        <table 
            id="${id}" name="${name}" 
            class="table table-bordered edit-black ${classes}"
        >
            <colgroup>
                <col width="15%"/>
                <col width="35%"/>
                <col width="15%"/>
                <col width="35%"/>
            </colgroup>
        """
        out << body()
        out << """
        </table>
        """
    }

    /**
     * 文字
     */
    Closure textField = { attrs ->

        if (!attrs.name) {
            throwTagError("Tag [textField] is missing required attribute [name]")
        }

        String cssClass = "form-control "+(attrs.remove("class")?:"")

        String style = attrs.remove("style")?:""


        boolean uppercase = attrs.remove("uppercase")?:false //全部轉換成大寫
        boolean lowercase = attrs.remove("lowercase")?:false //全部轉換成小寫
        boolean capitalize = attrs.remove("capitalize")?:false //將每個單字的第一個字母轉換成大寫

        if(uppercase){
            style += ";text-transform:uppercase"
        }else if(lowercase){
            style += ";text-transform:lowercase"
        }else if(capitalize){
            style += ";text-transform:capitalize"
        }

        style = style.replaceFirst(";","")

        attrs.type = "text"
        attrs.class = cssClass
        attrs.style = style
        fieldImpl(out, attrs)
    }

    /**
     * 數字
     */
    Closure numberField = { attrs ->

        if (!attrs.name) {
            throwTagError("Tag [textField] is missing required attribute [name]")
        }

        attrs.type = "number"
        out << inpitField(attrs)
    }

    /**
     * textarea
     */
    Closure textarea  = { attrs ->

        String name = attrs.remove("name")?:""
        String id = attrs.remove("id")?:name

        String cssClass = attrs.remove("cssClass")?:""
        String value = attrs.remove("value")?:""
        boolean readonly = attrs.remove("readonly")?:false


        out << "<textarea id='${id}' name='${name}'  class='form-control ${cssClass}' id=\"exampleFormControlTextarea1\" rows=\"3\" ${readonly}>${value}</textarea>"
    }

    /**
     * 按鈕
     */
    Closure button = {attrs,body ->

        String inputName = attrs.remove('name')?:UUID.randomUUID().toString()
        String id = attrs.remove('id')?:"${inputName}-button"
        String type = attrs.remove('inputType')?:"button"
        String classes = attrs.remove('class')?:"btn-primary"
        String onclick = attrs.remove('onclick')?:""

        def writer = out

        writer << "<button name='${inputName}' id='${id}' type=\"${type}\" class=\"btn ${classes} \" "
        if(onclick != ""){
            writer << " onclick=\"${onclick}\" "
        }
        outputAttributes(attrs, writer)
        writer << ">"
        writer << body()
        writer << "</button>"
    }

    /**
     * bootstrap-select
     */
    Closure multipleSelect = { attrs ->
        if (!attrs.name) {
            throwTagError("Tag [select] is missing required attribute [name]")
        }
        if (!attrs.containsKey('from')) {
            throwTagError("Tag [select] is missing required attribute [from]")
        }

        String id = attrs.remove('id')?:attrs.name
        String name = attrs.remove('name')
        String value = attrs.remove('value').toString()

        def writer = out
        def selectClass = attrs.remove('class')
        ArrayList from = attrs.remove('from')
        boolean selectDisabled = attrs.remove('selectDisabled')?:false
        String onclick = attrs.remove("onchange")
        String onchange = attrs.remove("onchange")
        String nextSelectId = attrs.remove("nextSelectId")
        String nextSelectUrl = attrs.remove("nextSelectUrl")

        String optionKey = attrs.remove('optionKey')
        String optionValue = attrs.remove('optionValue')

        boolean multiple = attrs.remove('multiple')
        String placeholder = attrs.remove('placeholder')

        def noSelection = attrs.remove('noSelection')
        if (noSelection != null) {
            noSelection = noSelection.entrySet().iterator().next()
        }
        booleanToAttribute(attrs, 'disabled')
        booleanToAttribute(attrs, 'readonly')

        writer << "<select "

        writer << "id='${id}' "
        writer << "name='${name}' "

        writer << "class=' form-control w-auto d-inline "
        if(selectClass){
            writer << selectClass
        }
        writer << " '"

        outputAttributes(attrs, writer, true)
        if(multiple){
            writer << "multiple"
        }

        if(selectDisabled){
            writer << " disabled "
        }

        if(onclick){
            writer << " onclick='${onclick}' "
        }

        if(onchange){
            writer << " onchange='${onchange}' "
        }

        /**
         * 下階層選單
         */
        if(nextSelectUrl && nextSelectId){
            writer << """onchange='ajaxChangSelectOption(this.value,"${nextSelectId}","${nextSelectUrl}");'"""
        }

        if(placeholder){
            writer << " placeholder='${placeholder}' "
        }

        writer << '>'
        writer.println()

        if (noSelection) {
            renderNoSelectionOptionImpl(writer, noSelection.key, noSelection.value, value)
            writer.println()
        }

        writer << optionList(from,value,optionKey,optionValue)
        // close tag
        writer << '</select>'
    }

    /**
     * 日期選擇器
     */
    Closure datepicker = { attrs, body ->
        if (!attrs.name) {
            throwTagError("Tag [datepicker] is missing required attribute [name]")
        }

        String id = attrs.remove('id')?:(UUID.randomUUID().toString())
        String name = attrs.remove('name')
        def value = attrs.remove('value')?:""
        String Class = attrs.remove('class')?:""
        boolean readonly = attrs.remove('readonly')?:false
        String formattedDate
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")

        if(value instanceof String){
            if(value != ""){
                def date = new Date().parse("yyyy-MM-dd HH:mm:ss.f", value)
                formattedDate = date.format("yyyy-MM-dd")
            }
            else {
                formattedDate = ""
            }
        }
        else if(value instanceof Timestamp){
            formattedDate = sdf.format(value)
        }
        else if(value instanceof Date){
            formattedDate = sdf.format(value)
        }
        else{
            formattedDate = ""
        }


        out << "<input id='${id}' name='${name}' value='${formattedDate}' class='form-control ${Class}' ${readonly?"readonly":""} style='width: 120px;display: inline-block;'>"
        out << """
                <script type="text/javascript">
                    jQuery( function() {
                        jQuery( "#${id}" ).datepicker({
                            showButtonPanel: true,
                            changeYear: true,
                            changeMonth: true,
                            dateFormat:'yy-mm-dd',
                            showWeek: true,
                            firstDay: 1
                        });
                    } );
                </script>
            """
    }

    Closure formItem = { attrs,body ->
        if (!attrs.name) {
            throwTagError("Tag [formItem] is missing required attribute [name]")
        }
        if (!attrs.labelTitle) {
            throwTagError("Tag [formItem] is missing required attribute [labelTitle]")
        }
        def inputName = attrs.remove('name')
        def id = attrs.remove('id')?:inputName
        def type = attrs.remove('inputType')?:"text"
        def placeholder = attrs.remove('placeholder')?:""
        def value = attrs.remove('value')?:""
        def inputClass = attrs.remove('inputClass')?:""
        ArrayList titleVsContent = attrs.remove('titleVsContent')?:[2,4]

        def labelId = attrs.remove('labelId')?:UUID.randomUUID().toString()
        def labelTitle = attrs.remove('labelTitle')?:""
        def labelClass = attrs.remove('labelClass')?:""


        def writer = out

        writer << "<label id='${labelId}' for=\"${id}\" class=\"col-sm-${titleVsContent[0]} table-primary col-form-label text-right ${labelClass}\">${labelTitle}</label>"
        writer << "<div class=\"col-sm-${titleVsContent[1]}\">"
        if(type=="contents"){
            //input 內容在標籤之間
            writer << body()
        }
        else {
            writer << "    <input type=\"${type}\" name=\"${inputName}\" id=\"${id}\" class='form-control ${inputClass}' placeholder=\"${placeholder}\" value=\"${value}\">"
        }
        writer << "</div>"

    }

    Closure checkBox = {attrs ->
        if (!attrs.name) {
            throwTagError("Tag [checkBox] is missing required attribute [name]")
        }
        def inputName = attrs.remove('name')
        def inputId = attrs.remove('id')?:inputName
        def inputClass = attrs.remove('inputClass')?:""
        def value = attrs.remove('value')?:""

        def labelId = attrs.remove('labelId')?:UUID.randomUUID().toString()
        def labelTitle = attrs.remove('labelTitle')?:""
        def labelClass = attrs.remove('labelClass')?:""

        def writer = out

        writer << "<input class=\"form-check-input ${inputClass}\" type=\"checkbox\" value=\"${value}\" id=\"${inputId}\">"
        writer << "<label id=\"${labelId}\" class=\"form-check-label ${labelClass}\" for=\"${inputId}\">${labelTitle}</label>"
    }


    /**
     * Dump out attributes in HTML compliant fashion.
     */
    @CompileStatic
    private void outputAttributes(Map attrs, GrailsPrintWriter writer, boolean useNameAsIdIfIdDoesNotExist = false) {
        attrs.remove('tagName') // Just in case one is left
        Encoder htmlEncoder = codecLookup?.lookupEncoder('HTML')
        attrs.each { k, v ->
            if (v != null) {
                writer << k
                writer << '="'
                writer << (htmlEncoder != null ? htmlEncoder.encode(v) : v)
                writer << '" '
            }
        }
        if (useNameAsIdIfIdDoesNotExist) {
            outputNameAsIdIfIdDoesNotExist(attrs, writer)
        }
    }
    @CompileStatic
    private void outputNameAsIdIfIdDoesNotExist(Map attrs, GrailsPrintWriter out) {
        if (!attrs.containsKey('id') && attrs.containsKey('name')) {
            Encoder htmlEncoder = codecLookup?.lookupEncoder('HTML')
            out << 'id="'
            out << (htmlEncoder != null ? htmlEncoder.encode(attrs.name) : attrs.name)
            out << '" '
        }
    }
    /**
     * Some attributes can be defined as Boolean values, but the html specification
     * mandates the attribute must have the same value as its name. For example,
     * disabled, readonly and checked.
     */
    @CompileStatic
    private void booleanToAttribute(Map attrs, String attrName) {
        def attrValue = attrs.remove(attrName)
        if (attrValue instanceof CharSequence) {
            attrValue = attrValue.toString().trim()
        }
        // If the value is the same as the name or if it is a boolean value,
        // reintroduce the attribute to the map according to the w3c rules, so it is output later
        if ((attrValue instanceof Boolean && attrValue) ||
                (attrValue instanceof String && (((String)attrValue).equalsIgnoreCase("true") || ((String)attrValue).equalsIgnoreCase(attrName)))) {
            attrs.put(attrName, attrName)
        } else if (attrValue instanceof String && !((String)attrValue).equalsIgnoreCase("false")) {
            // If the value is not the string 'false', then we should just pass it on to
            // keep compatibility with existing code
            attrs.put(attrName, attrValue)
        }
    }

    /**
     * 產生input
     * @param attrs
     * @return
     */
    private inpitField(attrs){
        String returnVal = ""

        if (!attrs.type) {
            throwTagError("Tag [inpitField] is missing required attribute [type]")
        }

        String classes = attrs.remove('class')?:""
        String name = attrs.remove('name')?:""
        String id = attrs.remove('id')?:name
        String value = attrs.remove('value')?:""
        String placeholder = attrs.remove('placeholder')?:""
        String style = attrs.remove('style')?:""
        String type = attrs.type?:"text"
        def size = attrs.remove('size')
        boolean readonly = attrs.remove('readonly')?:false
        boolean reset = attrs.remove('reset')?:false

        returnVal = "<input id='${id}' name='${name}' value='${value}'  type='${type}' placeholder='${placeholder}' class='form-control ${reset?"reset":""} ${classes}' ${size?" size='"+size+"'":""} style='${style}' ${readonly?"readonly":""}/>"

        return returnVal
    }

    /**
     * 未選擇選項
     * @param out
     * @param noSelectionKey
     * @param noSelectionValue
     * @param value
     * @return
     */
    def renderNoSelectionOptionImpl(out, noSelectionKey, noSelectionValue, value) {
        // If a label for the '--Please choose--' first item is supplied, write it out
        out << "<option value=\"${(noSelectionKey == null ? '' : noSelectionKey)}\"${noSelectionKey == value ? ' selected="selected"' : ''}>${noSelectionValue.encodeAsHTML()}</option>"
    }

    /**
     *
     * @param from
     * @param value
     * @param optionKey
     * @param optionValue
     * @return option清單
     */
    private String optionList(ArrayList from,String value,String optionKey,String optionValue){
        StringBuffer returnValue = new StringBuffer()

        // create options from list
        from.eachWithIndex {el, i ->
            def keyValue = el[optionKey]
            def text = el[optionValue]
            boolean selected
            returnValue.append('<option ')
            returnValue.append(" value='${keyValue}'' ")

            selected = value.equals(keyValue.toString())

            if(selected){
                returnValue.append("selected")
            }
            returnValue.append(' >')
            if(!keyValue.equals('')){
                returnValue.append("${i+1}.${text}")
            }
            else{
                returnValue.append("${text}")
            }

            returnValue.append('</option>')
        }

        return returnValue.toString()
    }

    @CompileStatic
    def fieldImpl(GrailsPrintWriter out, Map attrs) {
        resolveAttributes(attrs)

        attrs.value = processFormFieldValueIfNecessary(attrs.name, attrs.value, attrs.type)

        out << "<input type=\"${attrs.remove('type')}\" "
        outputAttributes(attrs, out, true)
        out << "/>"
    }

    /**
     * Check required attributes, set the id to name if no id supplied, extract bean values etc.
     */
    void resolveAttributes(Map attrs) {
        if (!attrs.name && !attrs.field) {
            throwTagError("Tag [${attrs.tagName}] is missing required attribute [name] or [field]")
        }

        attrs.remove('tagName')

        def val = attrs.remove('bean')
        if (val) {
            if (attrs.name.indexOf('.')) {
                attrs.name.split('\\.').each {val = val?."$it"}
            }
            else {
                val = val[name]
            }
            attrs.value = val
        }
        attrs.value = attrs.value != null ? attrs.value : "" // can't use ?: since 0 is groovy false

        booleanAttributes.each {
            booleanToAttribute(attrs, it)
        }
    }

    private processFormFieldValueIfNecessary(name, value, type) {
        if (requestDataValueProcessor != null) {
            return requestDataValueProcessor.processFormFieldValue(request, name, "${value}", type)
        }
        return value
    }
}
