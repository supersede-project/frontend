package eu.supersede.fe.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.supersede.fe.model.Application;
import eu.supersede.fe.util.ApplicationsLoader;

@RestController
@RequestMapping("/applicationlist")
public class ApplicationsRest
{
    @SuppressWarnings("unused")
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ApplicationsLoader applicationsLoader;

    @RequestMapping("")
    public List<Application> getApplications(HttpServletRequest request)
    {
        return applicationsLoader.getApplications();
    }
}