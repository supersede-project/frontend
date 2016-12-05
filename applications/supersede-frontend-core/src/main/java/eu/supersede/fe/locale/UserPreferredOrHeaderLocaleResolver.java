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

package eu.supersede.fe.locale;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.LocaleResolver;

import eu.supersede.fe.security.DatabaseUser;

/**
 * Locale to be used in pages.
 */
public class UserPreferredOrHeaderLocaleResolver implements LocaleResolver
{
    /**
     * Return the locale to be used for the given request.
     */
    @Override
    public Locale resolveLocale(HttpServletRequest request)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Locale loc = request.getLocale();

        if ((authentication == null) || (authentication instanceof AnonymousAuthenticationToken))
        {
            return loc;
        }

        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null)
        {
            Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (user instanceof DatabaseUser)
            {
                DatabaseUser dbUser = (DatabaseUser) user;
                String l = dbUser.getLocale();

                if (l != null && !l.equals(""))
                {
                    loc = new Locale.Builder().setLanguage(l).build();
                }
            }
        }

        return loc;
    }

    /**
     * Throw an exception when trying to set the locale for the given request or response.
     */
    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale)
    {
        throw new UnsupportedOperationException("Cannot change locale - use a different locale resolution strategy");
    }
}