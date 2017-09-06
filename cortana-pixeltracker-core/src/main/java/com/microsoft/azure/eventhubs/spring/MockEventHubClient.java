package com.microsoft.azure.eventhubs.spring;

import com.microsoft.azure.eventhubs.*;
import com.microsoft.azure.servicebus.ServiceBusException;

import java.time.Instant;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;

public class MockEventHubClient implements IEventHubClient {

    Queue<byte[]> queue = new LinkedList<>();
    @Override
    public void sendSync(EventData eventData) throws ServiceBusException {

    }

    @Override
    public CompletableFuture<Void> send(EventData eventData) {
        byte[] bytes = eventData.getBytes();
        queue.add(bytes);
        return null;
    }

    @Override
    public void sendSync(Iterable<EventData> iterable) throws ServiceBusException {

    }

    @Override
    public CompletableFuture<Void> send(Iterable<EventData> iterable) {
        return null;
    }

    @Override
    public void sendSync(EventData eventData, String s) throws ServiceBusException {

    }

    @Override
    public CompletableFuture<Void> send(EventData eventData, String s) {
        return null;
    }

    @Override
    public void sendSync(Iterable<EventData> iterable, String s) throws ServiceBusException {

    }

    @Override
    public CompletableFuture<Void> send(Iterable<EventData> iterable, String s) {
        return null;
    }

    @Override
    public PartitionSender createPartitionSenderSync(String s) throws ServiceBusException, IllegalArgumentException {
        return null;
    }

    @Override
    public CompletableFuture<PartitionSender> createPartitionSender(String s) throws ServiceBusException {
        return null;
    }

    @Override
    public PartitionReceiver createReceiverSync(String s, String s1, String s2) throws ServiceBusException {
        return null;
    }

    @Override
    public CompletableFuture<PartitionReceiver> createReceiver(String s, String s1, String s2) throws ServiceBusException {
        return null;
    }

    @Override
    public PartitionReceiver createReceiverSync(String s, String s1, String s2, boolean b) throws ServiceBusException {
        return null;
    }

    @Override
    public CompletableFuture<PartitionReceiver> createReceiver(String s, String s1, String s2, boolean b) throws ServiceBusException {
        return null;
    }

    @Override
    public PartitionReceiver createReceiverSync(String s, String s1, Instant instant) throws ServiceBusException {
        return null;
    }

    @Override
    public CompletableFuture<PartitionReceiver> createReceiver(String s, String s1, Instant instant) throws ServiceBusException {
        return null;
    }

    @Override
    public PartitionReceiver createReceiverSync(String s, String s1, String s2, ReceiverOptions receiverOptions) throws ServiceBusException {
        return null;
    }

    @Override
    public CompletableFuture<PartitionReceiver> createReceiver(String s, String s1, String s2, ReceiverOptions receiverOptions) throws ServiceBusException {
        return null;
    }

    @Override
    public PartitionReceiver createReceiverSync(String s, String s1, String s2, boolean b, ReceiverOptions receiverOptions) throws ServiceBusException {
        return null;
    }

    @Override
    public CompletableFuture<PartitionReceiver> createReceiver(String s, String s1, String s2, boolean b, ReceiverOptions receiverOptions) throws ServiceBusException {
        return null;
    }

    @Override
    public PartitionReceiver createReceiverSync(String s, String s1, Instant instant, ReceiverOptions receiverOptions) throws ServiceBusException {
        return null;
    }

    @Override
    public CompletableFuture<PartitionReceiver> createReceiver(String s, String s1, Instant instant, ReceiverOptions receiverOptions) throws ServiceBusException {
        return null;
    }

    @Override
    public PartitionReceiver createEpochReceiverSync(String s, String s1, String s2, long l) throws ServiceBusException {
        return null;
    }

    @Override
    public CompletableFuture<PartitionReceiver> createEpochReceiver(String s, String s1, String s2, long l) throws ServiceBusException {
        return null;
    }

    @Override
    public PartitionReceiver createEpochReceiverSync(String s, String s1, String s2, boolean b, long l) throws ServiceBusException {
        return null;
    }

    @Override
    public CompletableFuture<PartitionReceiver> createEpochReceiver(String s, String s1, String s2, boolean b, long l) throws ServiceBusException {
        return null;
    }

    @Override
    public PartitionReceiver createEpochReceiverSync(String s, String s1, Instant instant, long l) throws ServiceBusException {
        return null;
    }

    @Override
    public CompletableFuture<PartitionReceiver> createEpochReceiver(String s, String s1, Instant instant, long l) throws ServiceBusException {
        return null;
    }

    @Override
    public PartitionReceiver createEpochReceiverSync(String s, String s1, String s2, long l, ReceiverOptions receiverOptions) throws ServiceBusException {
        return null;
    }

    @Override
    public CompletableFuture<PartitionReceiver> createEpochReceiver(String s, String s1, String s2, long l, ReceiverOptions receiverOptions) throws ServiceBusException {
        return null;
    }

    @Override
    public PartitionReceiver createEpochReceiverSync(String s, String s1, String s2, boolean b, long l, ReceiverOptions receiverOptions) throws ServiceBusException {
        return null;
    }

    @Override
    public CompletableFuture<PartitionReceiver> createEpochReceiver(String s, String s1, String s2, boolean b, long l, ReceiverOptions receiverOptions) throws ServiceBusException {
        return null;
    }

    @Override
    public PartitionReceiver createEpochReceiverSync(String s, String s1, Instant instant, long l, ReceiverOptions receiverOptions) throws ServiceBusException {
        return null;
    }

    @Override
    public CompletableFuture<PartitionReceiver> createEpochReceiver(String s, String s1, Instant instant, long l, ReceiverOptions receiverOptions) throws ServiceBusException {
        return null;
    }

    @Override
    public CompletableFuture<Void> onClose() {
        return null;
    }

    @Override
    public CompletableFuture<EventHubRuntimeInformation> getRuntimeInformation() {
        return null;
    }

    @Override
    public CompletableFuture<EventHubPartitionRuntimeInformation> getPartitionRuntimeInformation(String s) {
        return null;
    }
}
