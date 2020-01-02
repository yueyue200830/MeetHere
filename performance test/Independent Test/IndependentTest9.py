from locust import HttpLocust, TaskSet, task


class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        self.addOrder()


    @task(1)
    def addOrder(self):
        params = {
            "addOrderForm": "{\"revenue\":\"篮球馆\",\"phoneNumber\":\"12312312312\"," +
                        "\"date\":\"2019-12-10\",\"timeSlot\":\"2\"," +
                        "\"room\":\"4\",\"price\":\"30\"}",
            "id": 1

        }
        self.client.get('/addOrder', params = params)

class WebsiteUser(HttpLocust):
    host = 'http://localhost:4200'

    task_set = UserBehavior
    min_wait = 5000
    max_wait = 9000
