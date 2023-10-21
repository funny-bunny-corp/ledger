package com.paymentic.domain;

import com.paymentic.domain.ids.OwnerId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "shelf")
public class Shelf {

  @Id
  @Column(name = "shelf_id")
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private UUID id;
  private String name;
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name="id",column=@Column(name="owner_id")),
  })
  private OwnerId owner;
  @OneToMany(mappedBy = "shelf",cascade = CascadeType.ALL)
  private Set<JournalEntry> journals;
  public Shelf() {
  }
  public Shelf(String name, OwnerId owner) {
    this.name = name;
    this.owner = owner;
  }
  public static Shelf newShelf(String name, OwnerId owner){
    return new Shelf(name,owner);
  }
  public void addEntry(JournalEntry entry){
    this.journals.add(entry);
  }
  public UUID getId() {
    return id;
  }
  public String getName() {
    return name;
  }
  public OwnerId getOwner() {
    return owner;
  }
  public Set<JournalEntry> getJournals() {
    return journals;
  }

}
