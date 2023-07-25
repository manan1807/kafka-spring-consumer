package com.example.libraryeventlistener.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordManager {
	
	@EmbeddedId
	private PartOff number;
	private String topic;
	@Enumerated(EnumType.STRING)
	private LibraryEventType event_type;
	private String consumerName;
	@OneToOne
	@JoinColumn(name = "libraryEventiId")
	private LibraryEvent libraryEvent;

}
