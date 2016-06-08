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

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserGadgetKey implements Serializable {

	protected Long userId;
	protected Long gadgetId;
	
	public UserGadgetKey()
	{
		
	}
	
	public UserGadgetKey(Long userId, Long gadgetId)
	{
		this.userId = userId;
		this.gadgetId = gadgetId;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getGadgetId() {
		return gadgetId;
	}
	
	public void setGadgetId(Long gadgetId) {
		this.gadgetId = gadgetId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof UserGadgetKey)
		{
			UserGadgetKey other = (UserGadgetKey) obj;
			
			return userId.equals(other.userId) && gadgetId.equals(other.gadgetId);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return userId.hashCode() + gadgetId.hashCode();
	}
	
}
