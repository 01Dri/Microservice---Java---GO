package main

import (
	"fmt"
	"os"
	"os/signal"

	"github.com/confluentinc/confluent-kafka-go/kafka"
)

func main() {
	config := &kafka.ConfigMap{
		"bootstrap.servers":  "localhost:9092",
		"group.id":           "meu-grupo-consumidor",
		"auto.offset.reset":  "earliest",
		"enable.auto.commit": "false",
	}

	consumer, err := kafka.NewConsumer(config)
	if err != nil {
		fmt.Printf("Erro ao criar consumidor: %v\n", err)
		os.Exit(1)
	}
	defer consumer.Close()

	err = consumer.SubscribeTopics([]string{"product.create"}, nil)
	if err != nil {
		fmt.Printf("Erro ao se inscrever nos tópicos: %v\n", err)
		os.Exit(1)
	}

	sigchan := make(chan os.Signal, 1)
	signal.Notify(sigchan, os.Interrupt)

	run := true

	for run == true {
		select {
		case sig := <-sigchan:
			fmt.Printf("Sinal de interrupção recebido: %v\n", sig)
			run = false
		default:
			ev := consumer.Poll(100)
			if ev == nil {
				continue
			}

			switch e := ev.(type) {
			case *kafka.Message:
				fmt.Printf("Mensagem recebida: %s\n", e.Value)
			case kafka.Error:
				fmt.Printf("Erro no consumidor: %v\n", e)
				run = false
			}
		}
	}
}
