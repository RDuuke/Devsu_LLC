FROM nginx:1.27-alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY index.html app.js pico.min.css alpine.min.js /usr/share/nginx/html/
EXPOSE 80
