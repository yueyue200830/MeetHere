from locust import HttpLocust, TaskSet, task


class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        self.deleteMessage()


    @task(5)
    def deleteMessage(self):
        params = {
            "id": 1,
        }
        self.client.get('/deleteMessage',params = params)

class WebsiteUser(HttpLocust):
    host = 'http://localhost:4200'

    task_set = UserBehavior
    min_wait = 5000
    max_wait = 9000
