<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html>

    <head>
        <title>JioCoders | Portfolio</title>
        <style>
            body {
                font-family: 'Segoe UI', sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f5f7fa;
            }

            header,
            footer {
                background-color: #007bff;
                color: white;
                padding: 20px 0;
                text-align: center;
            }

            footer {
                font-size: 14px;
            }

            .container {
                max-width: 960px;
                margin: 30px auto;
                background: white;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            .profile {
                text-align: center;
            }

            .avatar {
                width: 140px;
                height: 140px;
                border-radius: 50%;
                border: 4px solid #007bff;
                object-fit: cover;
                display: block;
                margin: 0 auto;
            }

            h1 {
                margin-top: 15px;
                color: #007bff;
            }

            h2 {
                border-bottom: 2px solid #eee;
                padding-bottom: 5px;
                color: #444;
            }

            .section {
                margin-top: 30px;
            }

            .skills,
            .availability {
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
                margin-top: 10px;
            }

            .tag {
                background: #007bff;
                color: white;
                padding: 6px 12px;
                border-radius: 5px;
                font-size: 14px;
            }

            .project {
                margin-bottom: 20px;
            }

            .project-title {
                font-weight: bold;
                font-size: 16px;
                margin-bottom: 5px;
            }

            .location-box {
                font-size: 15px;
                color: #333;
                margin-top: 8px;
            }
        </style>
    </head>

    <body>

        <header>
            <h1>JioCoders Portfolio</h1>
            <p>Sr. Software Engineer @ HCL Info Pvt Ltd</p>
        </header>

        <div class="container">
            <div class="profile">
                <!-- <img class="avatar" src="https://i.pravatar.cc/300?img=12" alt="JioCoders Avatar"> -->
                <img class="avatar" src="/images/jiocoders.png" alt="JioCoders Avatar">
                <h1>JioCoders</h1>
                <div class="location-box">
                    📍 Current: <strong>Mumbai</strong> | Preferred: <strong>Delhi NCR</strong><br />
                    🧑‍💼 8 Years Experience | 🎓 MCA | 📍 Prayagraj, Uttar Pradesh
                </div>
            </div>

            <div class="section">
                <h2>Objective</h2>
                <p>As a dedicated Software Engineer, my objective is to architect and deliver high-performance
                    applications that solve real-world problems. I strive to combine clean code, scalable architecture,
                    and leadership to foster innovation and business growth.</p>
            </div>

            <div class="section">
                <h2>Skills</h2>
                <div class="skills">
                    <div class="tag">Spring Boot</div>
                    <div class="tag">Java</div>
                    <div class="tag">Microservices</div>
                    <div class="tag">Kotlin</div>
                    <div class="tag">Flutter</div>
                    <div class="tag">Dart</div>
                    <div class="tag">Swift</div>
                    <div class="tag">DevOps</div>
                    <div class="tag">CI/CD</div>
                    <div class="tag">Agile</div>
                    <div class="tag">Team Leadership</div>
                </div>
            </div>

            <div class="section">
                <h2>AWS Certification</h2>
                <p>Certified in AWS cloud technologies with hands-on experience in:</p>
                <ul>
                    <li>✅ Amazon S3 – storage and bucket policies</li>
                    <li>✅ EC2 – virtual machine setup and load balancing</li>
                    <li>✅ AWS Lambda – building serverless applications</li>
                </ul>
            </div>

            <div class="section">
                <h2>Projects – Spring Boot</h2>
                <div class="project">
                    <div class="project-title">🚗 Car Management System</div>
                    <p>Spring Boot REST-based backend for car sales, service scheduling, and tracking. Completion: 1
                        year.</p>
                </div>
                <div class="project">
                    <div class="project-title">💇 Saloon Management Portal</div>
                    <p>Microservice app for bookings, staff roles, payment APIs. Completion: 1 year.</p>
                </div>
                <div class="project">
                    <div class="project-title">🛍️ E-commerce Backend</div>
                    <p>Robust Spring Boot backend for orders, payments, product catalog. Completion: 1 year.</p>
                </div>
            </div>

            <div class="section">
                <h2>Projects – Flutter & Mobile</h2>
                <div class="project">
                    <div class="project-title">🛒 E-commerce Mobile App</div>
                    <p>Cross-platform app built with Flutter, Firebase Auth, and payment integration. Completion: 1
                        year.</p>
                </div>
                <div class="project">
                    <div class="project-title">📱 Saloon Booking App</div>
                    <p>Customer/admin mobile app for real-time salon scheduling. Completion: 1 year.</p>
                </div>
            </div>

            <div class="section">
                <h2>Education</h2>
                <p><strong>Master of Computer Applications (MCA)</strong></p>
            </div>

            <div class="section">
                <h2>Availability</h2>
                <div class="availability">
                    <div class="tag" style="background-color:#28a745;">Full-time</div>
                    <div class="tag" style="background-color:#17a2b8;">Part-time</div>
                    <div class="tag" style="background-color:#ffc107; color:#333;">Onsite</div>
                    <div class="tag" style="background-color:#6c757d;">Hybrid</div>
                </div>
            </div>
        </div>

        <footer>
            © 2025 JioCoders. All rights reserved.
        </footer>

    </body>

    </html>