package com.example.libraryeventlistener.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class LibraryEvent {
	
	@Id
	private String libraryEventId;
	@Enumerated(EnumType.STRING)
	private LibraryEventType eventType;
	@OneToOne(mappedBy = "libraryEvent", cascade = {CascadeType.ALL})
	@ToString.Exclude
	private Book book;
	@OneToOne(mappedBy = "libraryEvent", cascade = {CascadeType.ALL})
	@ToString.Exclude
	private RecordManager recordManager;

}
