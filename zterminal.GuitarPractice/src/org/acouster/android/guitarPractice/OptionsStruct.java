package org.acouster.android.guitarPractice;

public class OptionsStruct
{
	protected int optInt;
	protected boolean optBool;
	
	public OptionsStruct() {
		this(3, false);
	}
	public OptionsStruct(int optInt, boolean optBool) {
		super();
		this.optInt = optInt;
		this.optBool = optBool;
	}

	public int getOptInt() {
		return optInt;
	}
	public boolean isOptBool() {
		return optBool;
	}


	// singleton
	protected static OptionsStruct instance;
	public static OptionsStruct instance()
	{
		return instance;
	}
	public void makeInstance()
	{
		instance = this;
	}
}
