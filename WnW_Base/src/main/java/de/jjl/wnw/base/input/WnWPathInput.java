package de.jjl.wnw.base.input;

import de.jjl.wnw.base.util.path.WnWPath;

public class WnWPathInput implements WnWInput
{
	private WnWPath path;
	
	public WnWPathInput(WnWPath path)
	{
		this.path = path;
	}

	public WnWPath getPath()
	{
		return path;
	}
}
