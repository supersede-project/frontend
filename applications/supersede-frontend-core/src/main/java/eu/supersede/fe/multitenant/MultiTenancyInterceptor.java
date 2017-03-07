/*
   (C) Copyright 2015-2018 The SUPERSEDE Project Consortium

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
     http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package eu.supersede.fe.multitenant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import eu.supersede.fe.security.DatabaseUser;

public class MultiTenancyInterceptor extends HandlerInterceptorAdapter
{
    @SuppressWarnings("unused")
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception
    {
        String multiTenantId = null;
        String tmp = null;

        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null)
        {
            Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (user instanceof DatabaseUser)
            {
                tmp = ((DatabaseUser) user).getTenantId();
            }
        }

        if (tmp != null)
        {
            multiTenantId = tmp;
        }
        else
        {
            multiTenantId = req.getHeader("TenantId");
        }

        if (multiTenantId != null)
        {
            req.setAttribute("CURRENT_TENANT_IDENTIFIER", multiTenantId);
        }

        return true;
    }
}