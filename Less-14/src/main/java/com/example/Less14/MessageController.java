package com.example.Less14;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class MessageController {

    private List<Message> messages = new ArrayList<>(Arrays.asList(
            new Message(1, "Напоминание", "Не забыть зайти на консультацию!", LocalDateTime.of(2023, 9, 14, 19, 0)),
            new Message(2, "Встреча", "Встреча нашей команды по курсовой работе.", LocalDateTime.of(2023, 9,16,13,30)),
            new Message(3, "Напоминание", "День рождения Жени!", LocalDateTime.of(2023, 11,19,9,0)),
            new Message(4, "Чрезвычайная ситуация", "Пожалуйста, немедленно покиньте здание из-за пожарной сигнализации.", LocalDateTime.of(2023, 8,29,14,15))
    ));

    @GetMapping("/message/{id}")
    public Optional<Message> findMessageById(@PathVariable int id) {
        return messages.stream().filter(p -> p.getId() == id).findFirst();
    }
    @PostMapping("/messages")
    public Message addMessage(@RequestBody Message message) {
        messages.add(message);
        return message;
    }

    @PutMapping("/messages/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable int id, @RequestBody Message message) {
        int index = - 1;
        for (Message p : messages) {
            if (p.getId() == id) {
                index = messages.indexOf(p);
                messages.set(index, message);
            }
        }
        return index == -1
                ? new ResponseEntity<>(addMessage(message), HttpStatus.CREATED)
                : new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/messages/{id}")
    public void deleteMessage(@PathVariable int id) {
        messages.removeIf(p -> p.getId() == id);
    }

    @GetMapping("/messages")
    public Iterable<Message> getMessages() {

        return messages;
    }
}
