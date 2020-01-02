from locust import HttpLocust, TaskSet, task


class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        self.updateUserPassById()


    @task(1)
    def updateUserPassById(self):
        params = {
            "id": 276,
            "updateForm" :"{\"pass\": \"12345678-momo\",\"originalPass\": \"123456-momo\"}"
        }
        self.client.get("/updateUserPassById", params = params)

class WebsiteUser(HttpLocust):
    host = 'http://localhost:4200'

    task_set = UserBehavior
    min_wait = 5000
    max_wait = 9000
