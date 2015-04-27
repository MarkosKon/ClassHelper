package com.example.classhelper.model;

import java.io.Serializable;

public abstract class Model implements Serializable
{
	private static final long serialVersionUID = -3356038601706060364L;
	public abstract long getId();
	public abstract void setId(long id);
}
