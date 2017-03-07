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

package eu.supersede.fe.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "users_gadgets")
@IdClass(UserGadgetKey.class)
public class UserGadget
{
    @Id
    private Long userId;
    @Id
    private Long gadgetId;
    private String applicationName;
    private String gadgetName;
    private Long panel;

    public UserGadget()
    {

    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getGadgetId()
    {
        return gadgetId;
    }

    public void setGadgetId(Long gadgetId)
    {
        this.gadgetId = gadgetId;
    }

    public String getApplicationName()
    {
        return applicationName;
    }

    public void setApplicationName(String applicationName)
    {
        this.applicationName = applicationName;
    }

    public String getGadgetName()
    {
        return gadgetName;
    }

    public void setGadgetName(String gadgetName)
    {
        this.gadgetName = gadgetName;
    }

    public Long getPanel()
    {
        return panel;
    }

    public void setPanel(Long panel)
    {
        this.panel = panel;
    }
}