from locust import HttpLocust, TaskSet, task


class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        self.updateUserNameById()



    @task(1)
    def updateUserNameById(self):
        params = {
            "id": 276,
            "newName": "momo"
        }
        self.client.get("/updateUserNameById", params = params)

class WebsiteUser(HttpLocust):
    host = 'http://localhost:4200'

    task_set = UserBehavior
    min_wait = 5000
    max_wait = 9000
