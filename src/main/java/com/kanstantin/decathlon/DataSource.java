package com.kanstantin.decathlon;

import com.kanstantin.decathlon.model.Participant;

import java.util.List;

public interface DataSource {
    List<Participant> loadData();
}
