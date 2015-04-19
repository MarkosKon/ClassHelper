package com.example.classhelper.model;

import java.io.Serializable;

public abstract class Model implements Serializable
{
	public abstract long getId();
	public abstract void setId(long id);
}
