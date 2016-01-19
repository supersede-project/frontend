package eu.supersede.fe.application;

import java.util.Comparator;

public class ApplicationGadgetComparator implements Comparator<ApplicationGadget>
{
	@Override
	public int compare(ApplicationGadget o1, ApplicationGadget o2)
	{
		return o1.getId().compareTo(o2.getId());
	}
}
