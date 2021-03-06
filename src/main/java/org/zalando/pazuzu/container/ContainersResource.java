package org.zalando.pazuzu.container;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.zalando.pazuzu.exception.NotFoundException;
import org.zalando.pazuzu.exception.ServiceException;
import org.zalando.pazuzu.feature.FeatureDto;
import org.zalando.pazuzu.feature.FeatureToAddDto;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/containers")
public class ContainersResource {

    private final ContainerService containerService;

    @Autowired
    public ContainersResource(ContainerService containerService) {
        this.containerService = containerService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ContainerDto> listContainers(@RequestParam(required = false, defaultValue = "") String name) {
        return containerService.listContainers(name, ContainerDto::ofShort);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContainerFullDto> createContainer(@RequestBody ContainerToCreateDto value, UriComponentsBuilder uriBuilder) throws ServiceException {
        ContainerFullDto container = containerService.createContainer(value.getName(), value.getFeatures(), ContainerFullDto::buildFull);
        return ResponseEntity
                .created(uriBuilder.path("/api/containers/{containerName}").buildAndExpand(container.getName()).toUri())
                .body(container);
    }

    @RequestMapping(value = "/{containerName}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ContainerFullDto updateContainer(@PathVariable String containerName, @RequestBody ContainerToCreateDto value) throws ServiceException {
        return containerService.updateContainer(containerName, value.getName(), value.getFeatures(), ContainerFullDto::buildFull);
    }

    @RequestMapping(value = "/{containerName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ContainerFullDto getContainer(@PathVariable String containerName) throws ServiceException {
        return containerService.getContainer(containerName, ContainerFullDto::buildFull);
    }

    @RequestMapping(value = "/{containerName}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteContainer(@PathVariable String containerName) throws NotFoundException {
        containerService.deleteContainer(containerName);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{containerName}/features", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FeatureDto> getContainerFeatures(@PathVariable String containerName) throws ServiceException {
        return containerService.getContainer(containerName, ContainerFullDto::buildFull).getFeatures();
    }

    @RequestMapping(value = "/{containerName}/features", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ContainerFullDto addFeatureToContainer(@PathVariable String containerName, @RequestBody FeatureToAddDto feature) throws ServiceException {
        return containerService.addFeature(containerName, feature.getName(), ContainerFullDto::buildFull);
    }

    @RequestMapping(value = "/{containerName}/features/{featureName}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteContainerFeature(@PathVariable String containerName, @PathVariable String featureName) throws ServiceException {
        containerService.deleteFeature(containerName, featureName);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{containerName}/dockerfile", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String getDockerFile(@PathVariable String containerName) throws ServiceException {
        return containerService.generateDockerfile(containerName);
    }
}
