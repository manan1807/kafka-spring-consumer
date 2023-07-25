package com.example.libraryeventlistener.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartOff implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String partitionNumber;
	private String offsetNumber;

}
