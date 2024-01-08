package eu.telecomnancy.labfx.model;

import java.time.Duration;
import java.util.Date;

public class Ad {
  public String name;
  public boolean isOffer;
  public AdType type;
  public String description;
  public String imagePath;
  public int cost;
  public String address;
  public double maxDistance;
  public Date start;
  public Date end;
  public Duration duration;
}
