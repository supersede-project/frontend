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

package eu.supersede.fe.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class CsrfRequestMatcher implements RequestMatcher
{
    private String[] permitUrls;
    private String[] allowedMethods = { "GET", "HEAD", "TRACE", "OPTIONS" };

    private static final List<RequestMatcher> matchers = new ArrayList<>();

    public CsrfRequestMatcher(String[] permitUrls)
    {
        this.permitUrls = permitUrls;

        for (String m : allowedMethods)
        {
            matchers.add(new AntPathRequestMatcher("/**", m));
        }
        for (String u : this.permitUrls)
        {
            matchers.add(new AntPathRequestMatcher(u, null));
        }
    }

    @Override
    public boolean matches(HttpServletRequest request)
    {
        for (RequestMatcher rm : matchers)
        {
            if (rm.matches(request))
            {
                return false;
            }
        }

        // CSRF for everything else that is not an API call or an allowedMethod
        return true;
    }
}