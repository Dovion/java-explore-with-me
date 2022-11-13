package ru.practicum.explorewithme.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event")
public class Location {

    @Column(name = "event_location_lon")
    private double longitude;
    @Column(name = "event_location_lat")
    private double latitude;
}
