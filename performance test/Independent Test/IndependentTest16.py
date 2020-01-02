from locust import HttpLocust, TaskSet, task


class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        self.getLatestMessage()


    @task(4)
    def getLatestMessage(self):
        self.client.post("/getLatestMessage")

class WebsiteUser(HttpLocust):
    host = 'http://localhost:4200'

    task_set = UserBehavior
    min_wait = 5000
    max_wait = 9000
