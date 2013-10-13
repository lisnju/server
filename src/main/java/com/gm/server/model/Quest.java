package com.gm.server.model;

import java.util.Date;

import com.gm.common.model.Rpc.Currency;
import com.gm.common.model.Rpc.GeoPoint;
import com.gm.common.model.Rpc.LifeSpan;
import com.gm.common.model.Rpc.QuestPb;
import com.gm.common.model.Rpc.Applicants;
import com.gm.common.model.Rpc.PostRecordsPb;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.Link;
import com.google.appengine.api.datastore.PostalAddress;

@Entity
public class Quest extends Persistable<Quest> {


  @Property
  private Applicants.Builder applicants = Applicants.newBuilder();
  
  @Property
  private Date start_time = new Date();

  @Property
  private Date end_time ;//= new Date();
  
  @Property
  private String title="";

  @Property
  private PostalAddress address;//=new PostalAddress("");

  @Property
  private GeoPt geo_point;//=new GeoPt(0,0);
  
  @Property
  private long prize=0; // at owner's view: <0 give reward, >0 collect reward
  
  @Property
  private String description="";
  
  @Property
  private boolean allow_sharing = false;
  @Property
  private Link attach_link;//=new Link("");
  
  @Property
  private PostRecordsPb.Builder posts=PostRecordsPb.newBuilder();


  public Applicants.Builder getApplicants() {
    return applicants;
  }

  public void setApplicants(Applicants.Builder applicants) {
    this.applicants = applicants;
  }

  public long getPrize() {
    return prize;
  }

  public void setPrize(long prize) {
    this.prize = prize;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isAllow_sharing() {
    return allow_sharing;
  }

  public void setAllow_sharing(boolean allow_sharing) {
    this.allow_sharing = allow_sharing;
  }

  public Link getAttach_link() {
    return attach_link;
  }

  public void setAttach_link(Link attach_link) {
    this.attach_link = attach_link;
  }

  public PostRecordsPb.Builder getPosts() {
    return posts;
  }

  public void setPosts(PostRecordsPb.Builder posts) {
    this.posts = posts;
  }


  
  public Quest(){
  }
  
  public Quest(String title){
    this.title = title;
  }

  public Quest(QuestPb q) {
    start_time = new Date(q.getLifespan().getCreateTime());
    end_time = new Date(q.getLifespan().getDeleteTime());
    title = q.getTitle();
    address = new PostalAddress(q.getAddress());
    geo_point = new GeoPt(q.getGeoPoint().getLatitude(),q.getGeoPoint().getLongitude());
    prize = q.getReward().getGold();
    description = q.getDescription();
    allow_sharing = q.getAllowSharing();
    attach_link = new Link(q.getUrl());
  }
  
  QuestPb.Builder getMSG(){
    LifeSpan.Builder lifespan = LifeSpan.newBuilder().setCreateTime(start_time.getTime());
    if(end_time!=null)lifespan.setDeleteTime(end_time.getTime());
    
    GeoPoint.Builder gmsg=null;
    if(geo_point!=null){
       gmsg= GeoPoint.newBuilder().setLatitude(geo_point.getLatitude()).setLongitude(geo_point.getLongitude());
    }
    Currency reward = Currency.newBuilder().setGold(prize).build();
    QuestPb.Builder qMsg = QuestPb.newBuilder().setLifespan(lifespan)
        .setTitle(title).setReward(reward).setDescription(description)
        .setAllowSharing(allow_sharing);
    
    if(entity!=null){
      qMsg.setId(entity.getKey().getId()).setOwnerId(entity.getParent().getId());
    }
    if(address!=null){
      qMsg.setAddress(address.getAddress());
    }
    if(gmsg!=null){
      qMsg.setGeoPoint(gmsg);
    }
    if(attach_link!=null){
      qMsg.setUrl(attach_link.getValue());
    }

    return qMsg;
  }

  public Date getStart_time() {
    return start_time;
  }

  public void setStart_time(Date start_time) {
    this.start_time = start_time;
  }

  public Date getEnd_time() {
    return end_time;
  }

  public void setEnd_time(Date end_time) {
    this.end_time = end_time;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public PostalAddress getAddress() {
    return address;
  }

  public void setAddress(PostalAddress address) {
    this.address = address;
  }

  public GeoPt getGeo_point() {
    return geo_point;
  }

  public void setGeo_point(GeoPt geo_point) {
    this.geo_point = geo_point;
  }

  @Override
  public Quest touch() {
    // TODO Auto-generated method stub
    return null;
  }

}

