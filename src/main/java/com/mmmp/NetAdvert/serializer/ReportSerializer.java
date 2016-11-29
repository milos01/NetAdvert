package com.mmmp.NetAdvert.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mmmp.NetAdvert.model.Report;

public class ReportSerializer extends JsonSerializer<Report> {

//@JsonSerialize(using = ReportSerializer.class)	
//	public ReportSerializer() {
//        this(null);
//    }
   
//    public ReportSerializer(Class<Report> t) {
//        super(t);
//    }

	@Override
	public void serialize(Report value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("description", value.getReportDescription());
        jgen.writeObjectField("user", value.getUser());
        jgen.writeObjectField("advert", value.getAdvert());
        jgen.writeEndObject();
        
		
	}
	
	@Override
    public Class<Report> handledType() {
        return Report.class;
    }
    

}
