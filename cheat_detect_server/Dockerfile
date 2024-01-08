# Use an official Python runtime as a parent image
FROM python:3.10

# Set the working directory in the container to /app
WORKDIR /app

# Install any needed packages specified in requirements.txt
RUN apt-get update && apt-get install -y \
    libgl1-mesa-glx \
    libsm6 \
    libxext6

RUN pip install "fastapi[all]"
RUN pip install opencv-python

COPY . /app/

# Run app.py when the container launches
CMD ["python3", "run.py"]
