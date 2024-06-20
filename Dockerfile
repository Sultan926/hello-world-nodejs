# Dockerfile
# Use the official Node.js 14 image
FROM node:14

# Set the working directory inside the container
WORKDIR /app

# Copy package.json and package-lock.json to the working directory
COPY package*.json app.js ./

# Install Node.js dependencies
RUN npm install




# Expose the port the app runs on
EXPOSE 3000

# Command to run the application
CMD ["node", "app.js"]
