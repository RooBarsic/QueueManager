# Queue API

Queue API allows you to create and manage queues

Opportunities:
  - Create queue
  - Show you list of queue
  - Add customers to queue 
  - Delete customer from queue
  - Show selected queue


#  Customer
We assume that the registration of the user in the queue will occur with a mobile phone, therefore, in requests from the user there is always a mobile phone



# REST-requests
For example, we use curl-utility
There are some REST-requests you can use to communicate with our API

```sh
  curl 'localhost:8000/api/addNewQueue?queueName=SOBES'
  ```
By this request you can create your own queue.  You can specify queue name by REST parameter.
  
```sh
  curl 'localhost:8000/api/getAllQueues'
  ```
This request will show you list of all created queues.
```sh
curl 'localhost:8000/api/addToQueue?queueName=Sberbank&phoneNumber=89212255432'
 ```
 
In order to add a customer to the queue, you can use this request

```sh
curl 'localhost:8000/api/deleteFromQueue?queueName=Sberbank&phoneNumber=89212255432'
 ```
 This request allows you to delete customers by the phone number
```sh
curl 'localhost:8000/api/getQueue?queueName=Sberbank'
 ```
This request will show you queue by the queue name
