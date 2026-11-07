import './navi.css';

export default function Header() {
    return (
        <header>
            <nav className="navbar">
                <div className="navbar-logo">Modeapp</div>

                <div className="navlinks">
                    <ul>
                        <li><a href="#">Home</a></li>
                        <li><a href="#">Dein Schrank</a></li>
                        <li><a href="#">Vorschläge für Outfits</a></li>
                        <li><a href="#">Styl-Polizei</a></li>
                        <li><a href="#">eigene Kombi hinterlegen</a></li>
                        <li><a href="#">Beratung</a></li>
                        <li><a href="#">Freunde adden</a></li>
                        <li><a href="#">About</a></li>
                    </ul>
                </div>
            </nav>
        </header>
    );
}