# Padel Planning & Matchmaking Platform

A robust web application designed to streamline the organization, scheduling, and player management for padel days. It features an intelligent, automated matchmaking algorithm alongside a dynamic, real-time notification system to handle player sign-ups, cancellations, and roster changes seamlessly.

---

## 🚀 Features

### 🛠️ Admin Management & Smart Scheduling
* **Padel Day Creation:** Admin dashboard to create, adjust, and publish upcoming padel events.
* **Algorithmic Matchmaking:** Automatically generates balanced court line-ups and schedules based on a strict priority hierarchy:
    1. Gender
    2. Player Ranking (`pRanking`)
    3. Preferred Play Side (Left/Right/None)
    4. Age

### 👥 Player Experience
* **Flexible Registration:** Easy sign-in and sign-out functionality for all registered players.
* **Dynamic Waitlists:** Automated moving of players between the "Signed Up" list and the "Reserve" list based on capacity and timing.

### 🔔 Automated Notification System
The platform features an advanced notification engine triggered by real-time event updates:
* **Event Launch:** Notifies all players when a new padel day is published.
* **Roster Promotions:** Automatically alerts a player when they move from the Reserve List to the Signed Up List.
* **Roster Demotions:** Alerts a player if they are bumped back to the Reserve List (e.g., due to roster adjustments affecting the final four slots).
* **Urgent Openings:** Dispatches an **"Urgent Player Needed"** alert if a signed-up player drops out and the reserve list is empty.
* **Schedule Shifts:** Alerts affected players when an active padel day's details are adjusted.
* **New Registrations:** Sends an exclusive notification to the Admin whenever a new player registers on the platform.

---

## 🛠️ Tech Stack

The application is built using a reliable, full-stack architecture:

* **Backend:** Java
* **Frontend:** JavaScript, HTML5, CSS3
* **Database:** SQL (Relational Database Management)

---

## 📦 Getting Started

### Prerequisites
Before running this project locally, ensure you have the following installed:
* Java Development Kit (JDK) 17 or higher
* A compatible SQL Database (e.g., MySQL, PostgreSQL)
* A modern web browser

### Installation
1. **Clone the repository:**
```bash
   git clone [https://github.com/justinvnbk/padelplanning](https://github.com/justinvnbk/padelplanning)
   cd padelplanning