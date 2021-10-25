FROM python:3.7

COPY ./ ./app
WORKDIR /app

RUN pip install -r requirements.txt

EXPOSE 4000

CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "4000"]