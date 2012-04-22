package com.ptibiscuit.onechance.data;

import com.ptibiscuit.onechance.models.Stats;

public abstract interface DataHandler
{
  public abstract Stats createStats(String paramString, int paramInt);

  public abstract void deleteStats(String paramString);

  public abstract void updateStats(String paramString, int paramInt);

  public abstract Stats getStats(String paramString);

  public abstract boolean checkActivity();
}

/* Location:           C:\Users\ANNA\Documents\Frederic\netbeanspace\OneChance\dist\OneChance.jar
 * Qualified Name:     com.ptibiscuit.onechance.data.DataHandler
 * JD-Core Version:    0.6.0
 */