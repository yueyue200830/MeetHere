from locust import HttpLocust, TaskSet, task


class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        self.register()


    @task(1)
    def register(self):
        params = {
            "form": "{\"name\": \"momo2\",\"password\": \"passwd\"}"
        }
        self.client.get("/Register", params = params)

class WebsiteUser(HttpLocust):
    host = 'http://localhost:4200'

    task_set = UserBehavior
    min_wait = 5000
    max_wait = 9000
