package com.example.classhelper.model;

import java.io.Serializable;

/**
 * This class provides id field for all concrete models.
 */
public abstract class Model implements Serializable
{
	private static final long serialVersionUID = -3356038601706060364L;
	public abstract long getId();
	public abstract void setId(long id);
}
