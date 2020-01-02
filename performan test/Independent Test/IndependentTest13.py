from locust import HttpLocust, TaskSet, task


class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        self.deleteOrderById()




    @task(5)
    def deleteOrderById(self):
        params = {
            "deleteOrderId": 2
        }
        self.client.get("/deleteOrder", params = params)

class WebsiteUser(HttpLocust):
    host = 'http://localhost:4200'

    task_set = UserBehavior
    min_wait = 5000
    max_wait = 9000
