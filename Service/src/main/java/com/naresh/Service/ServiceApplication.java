package com.naresh.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceApplication {

	
	  
	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}
}

@RestController
@RefreshScope
class ServiceInstanceRestController {

    @Autowired
    private DiscoveryClient discoveryClient;
    
    @Autowired
    private ServiceConfiguration properties;
	
	@Value("${some.other.property}")
	private String someOtherProperty;
  
    
    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
    	StringBuilder sb = new StringBuilder();
    	sb.append(properties.getProperty() +"!!"+ someOtherProperty);
        return this.discoveryClient.getInstances(applicationName);
    }
    
    @RequestMapping("/service-instances/getconfig")
    public String serviceInstancesGetConfig() {
    	StringBuilder sb = new StringBuilder();
    	sb.append(properties.getProperty() +"!!"+ someOtherProperty);
        return sb.toString();
    }
}

@Component
@ConfigurationProperties(prefix="some")
class ServiceConfiguration{
	
	private String property;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	
}