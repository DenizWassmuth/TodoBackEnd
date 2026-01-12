package org.example.todobackend.repos;

import org.example.todobackend.model.TodoRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepo extends MongoRepository<TodoRecord, String> {
}
